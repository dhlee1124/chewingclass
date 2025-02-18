package chewingclass.demo.controller;

import chewingclass.demo.entity.Event;
import chewingclass.demo.service.EventService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;

    @Value("${file.upload-dir}")  // application.properties에서 업로드 경로 가져오기
    private String uploadDir;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // 이벤트 목록 페이지
    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    // ✅ 진행 중인 이벤트 개수 JSON 반환
    @GetMapping("/ongoing-count")
    public int getOngoingEventCount() {
        return (int) eventService.getOngoingEventCount();
    }

    // 이벤트 저장 (이미지 업로드 포함)
    @PostMapping("/save")
    public Event saveEvent(@ModelAttribute Event event, @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
                Path filePath = Paths.get(uploadDir, fileName);
                imageFile.transferTo(new File(filePath.toString()));
                event.setImageUrl("/uploads/" + fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (event.getImageUrl() == null || event.getImageUrl().isEmpty()) {
            event.setImageUrl("/uploads/default-event.jpg");
        }

        return eventService.saveEvent(event);  // ✅ JSON 응답으로 이벤트 정보 반환
    }


    // 이벤트 상세보기
    @GetMapping("/{id}")
    public Optional<Event> viewEvent(@PathVariable Long id) {
        return eventService.getEventById(id);
    }

    // 이벤트 삭제
    @DeleteMapping("/delete/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }
}