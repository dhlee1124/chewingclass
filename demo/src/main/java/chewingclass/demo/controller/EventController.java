package chewingclass.demo.controller;

import chewingclass.demo.entity.Event;
import chewingclass.demo.service.EventService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;

    @Value("${file.upload-dir}")  // application.properties에서 업로드 경로 가져오기
    private String uploadDir;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // 이벤트 목록 페이지
    @GetMapping
    public String listEvents(Model model) {
        model.addAttribute("events", eventService.getAllEvents());
        model.addAttribute("ongoingEventCount", eventService.getOngoingEventCount());  // 진행 중인 이벤트 개수 추가
        return "event-list";
    }

    // 이벤트 등록 폼
    @GetMapping("/new")
    public String showEventForm(Model model) {
        model.addAttribute("event", new Event());
        return "event-form";
    }

    // 이벤트 저장 (이미지 업로드 포함)
    @PostMapping("/save")
    public String saveEvent(@ModelAttribute Event event, @RequestParam("imageFile") MultipartFile imageFile) {
        System.out.println("📢 업로드된 파일: " + (imageFile != null ? imageFile.getOriginalFilename() : "파일이 null입니다!"));

        try {
            if (imageFile != null && !imageFile.isEmpty()) {  // ✅ 파일이 존재하는지 확인
                String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
                Path filePath = Paths.get(uploadDir, fileName);
                imageFile.transferTo(new File(filePath.toString()));
                event.setImageUrl("/uploads/" + fileName);
                System.out.println("📢 저장된 이미지 경로: " + event.getImageUrl());
            } else {
                System.out.println("⚠️ 파일이 업로드되지 않음!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // ✅ `imageUrl`이 비어있다면 기본 이미지 설정
        if (event.getImageUrl() == null || event.getImageUrl().isEmpty()) {
            event.setImageUrl("/uploads/default-event.jpg");
            System.out.println("📢 기본 이미지 설정: " + event.getImageUrl());
        }

        eventService.saveEvent(event);
        return "redirect:/events";
    }




    // 이벤트 상세보기
    @GetMapping("/{id}")
    public String viewEvent(@PathVariable Long id, Model model) {
        Optional<Event> event = eventService.getEventById(id);
        if (event.isPresent()) {
            model.addAttribute("event", event.get());
            return "event-detail";
        }
        return "redirect:/events";
    }

    // 이벤트 수정 폼
    @GetMapping("/edit/{id}")
    public String editEvent(@PathVariable Long id, Model model) {
        Optional<Event> event = eventService.getEventById(id);
        if (event.isPresent()) {
            model.addAttribute("event", event.get());
            return "event-form";
        }
        return "redirect:/events";
    }

    // 이벤트 삭제
    @GetMapping("/delete/{id}")
    public String deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return "redirect:/events";
    }
}
