package com.ssafy.campinity.core.service.impl;

import com.ssafy.campinity.core.dto.*;
import com.ssafy.campinity.core.entity.curation.Curation;
import com.ssafy.campinity.core.entity.curation.CurationCategory;
import com.ssafy.campinity.core.entity.curation.CurationImage;
import com.ssafy.campinity.core.entity.curation.CurationScrap;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.repository.curation.CurationImageRepository;
import com.ssafy.campinity.core.repository.curation.CurationRepository;
import com.ssafy.campinity.core.repository.curation.CurationScrapRepository;
import com.ssafy.campinity.core.repository.member.MemberRepository;
import com.ssafy.campinity.core.service.CurationService;
import com.ssafy.campinity.core.utils.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurationServiceImpl implements CurationService {

    private final ImageUtil imageUtil;
    private final CurationRepository curationRepository;
    private final CurationImageRepository curationImageRepository;
    private final CurationScrapRepository curationScrapRepository;
    private final MemberRepository memberRepository;

    @Override
    public CurationMetaResDTO createCuration(CurationReqDTO curationReqDTO) throws IOException {
        List<MultipartFile> files = curationReqDTO.getFiles();
        List<String> imagePaths = new ArrayList<>();

        for (MultipartFile file: files) {
            String imagePath = imageUtil.uploadImage(file, "curation");
            imagePaths.add(imagePath);
        }

        String firstImagePath = null;

        if (imagePaths.size() > 0) {
            firstImagePath = imagePaths.get(0);
        }

        Curation curation = Curation.builder().uuid(UUID.randomUUID()).title(curationReqDTO.getTitle()).
                content(curationReqDTO.getContent()).firstImagePath(firstImagePath).
                curationCategory(curationReqDTO.getCurationCategory()).build();
        Curation savedCuration = curationRepository.save(curation);

        for (String imagePath: imagePaths) {
            CurationImage curationImage = CurationImage.builder().curation(savedCuration).imagePath(imagePath).build();
            curationImageRepository.save(curationImage);
        }

        return CurationMetaResDTO.builder().curation(savedCuration).build();
    }

    @Override
    public List<CurationMetaResDTO> getCurationBannerList() {
        return curationRepository.findRandomCuration().stream().map(curation -> {
            return CurationMetaResDTO.builder().curation(curation).build();
        }).collect(Collectors.toList());
    }

    @Override
    public List<CurationMetaResDTO> getCurationList(String curationCategory) {
        CurationCategory curationCategory1 = CurationCategory.fromParam(curationCategory);

        List<Curation> curations = new ArrayList<>();
        if (curationCategory1.getName().equals("ALL")) {
            curations = curationRepository.findAll();
        } else {
            curations = curationRepository.findByCurationCategory(curationCategory1);
        }

        return curations.stream().map(curation -> {
            return CurationMetaResDTO.builder().curation(curation).build();
        }).collect(Collectors.toList());
    }

    @Override
    public CurationDetailResDTO getCurationDetail(UUID curationId, int memberId) {
        Curation curation = curationRepository.findByUuid(curationId).orElseThrow(IllegalArgumentException::new);

        List<String> imagePaths = curationImageRepository.findByCuration_id(curation.getId()).stream().map(image -> {
            return image.getImagePath();
        }).collect(Collectors.toList());

        Boolean isScraped = curationScrapRepository.findByCuration_idAndMember_id(curation.getId(), memberId).isPresent();

        return CurationDetailResDTO.builder().curation(curation).imagePaths(imagePaths).isScraped(isScraped).build();
    }

    @Override
    public List<CurationScrapListResDTO> getCurationScrapList(int memberId) {
        List<Curation> scrapLists = curationRepository.findScrapList(memberId);
        return scrapLists.stream().map(curation -> {
            return CurationScrapListResDTO.builder().curation(curation).isScraped(true).build();
        }).collect(Collectors.toList());
    }

    @Override
    public IsScrapResDTO scrapCuration(UUID curationId, int memberId) {
        Curation curation = curationRepository.findByUuid(curationId).orElseThrow(IllegalArgumentException::new);
        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);

        Optional<CurationScrap> curationScrap = curationScrapRepository.findByCuration_idAndMember_id(curation.getId(), member.getId());

        Boolean isScraped = false;
        if (curationScrap.isPresent()) {
            curationScrapRepository.deleteById(curationScrap.get().getId());
        } else {
            curationScrapRepository.save(CurationScrap.builder().curation(curation).member(member).build());
            isScraped = true;
        }

        return IsScrapResDTO.builder().isScraped(isScraped).build();
    }
}
