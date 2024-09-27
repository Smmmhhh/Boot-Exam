package com.exam.smh.controller;

import com.exam.smh.dto.GuestbookDTO;
import com.exam.smh.dto.PageRequestDTO;
import com.exam.smh.dto.PageResultDTO;
import com.exam.smh.entity.GuestBook;
import com.exam.smh.service.GuestbookService;
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
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor // 자동 주입을 위한 Annotation
public class GuestBookController {

    private final GuestbookService service;

    @GetMapping("/")
    public String index() {
        return "redirect:/guestbook/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        PageResultDTO<GuestbookDTO, GuestBook> pageResultDTO = service.getList(pageRequestDTO);
        model.addAttribute("result", pageResultDTO);
    }

    @GetMapping("/register")
    public void register() {
        log.info("register get...");
    }

    @PostMapping("/register")
    public String registerPost(GuestbookDTO dto, RedirectAttributes redirectAttributes) {

        log.info("dto : " + dto);

        // 새로 추가된 엔티티의 PK
        Long gno = service.register(dto);

        redirectAttributes.addFlashAttribute("msg", gno);

        return "redirect:/guestbook/list";
    }

    @GetMapping("/read")
    public void read(Long gno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {

        GuestbookDTO dto = service.read(gno);
        log.info("read dto : " + dto);

        model.addAttribute("dto", dto);
    }
}
