package chewingclass.demo.service;

import chewingclass.demo.entity.Event;
import chewingclass.demo.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    // ✅ end_date 기준으로 정렬된 목록 반환
    public List<Event> getAllEvents() {
        return eventRepository.findAllByOrderByEndDateAsc();
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    // ✅ 현재 진행 중인 이벤트 개수 반환
    public long getOngoingEventCount() {
        return eventRepository.countOngoingEvents(LocalDate.now());
    }
}
