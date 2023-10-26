package com.mall.twins.twinsmall.controller;

import com.mall.twins.twinsmall.dto.NoticeDTO;
import com.mall.twins.twinsmall.dto.PageRequestDTO;
import com.mall.twins.twinsmall.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("notice")
@Log4j2
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {

        log.info("list.............." + pageRequestDTO);

        model.addAttribute("result", noticeService.getList(pageRequestDTO));

    }

    @GetMapping("/register")
    public void register() {
        log.info("register get....");
    }

    @PostMapping("/register")
    public String registerPost(NoticeDTO dto, RedirectAttributes redirectAttributes) {

        log.info("dto...." + dto);

        //새로추가된 엔티티번호임
        Long nno = noticeService.register(dto);

        redirectAttributes.addFlashAttribute("msg", nno);

        return "redirect:/notice/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(long nno, @ModelAttribute("requestDTO") PageRequestDTO
            requestDTO, Model model) {
        log.info("nno: " + nno);

        NoticeDTO dto = noticeService.read(nno);

        model.addAttribute("dto", dto);
    }

    @PostMapping("/remove")
    public String remove(long nno, RedirectAttributes redirectAttributes) {
        log.info("nno : " + nno);

        noticeService.remove(nno);

        redirectAttributes.addFlashAttribute("msg", nno);

        return "redirect:/notice/list";
    }

    @PostMapping("/modify")
    public String modify(NoticeDTO dto,
                         @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                         RedirectAttributes redirectAttributes) {


        log.info("post modify.........................................");
        log.info("dto: " + dto);

        noticeService.modify(dto);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());

        redirectAttributes.addAttribute("nno", dto.getNno());


        return "redirect:/notice/read";
    }

}