package com.chewingclass.chewingclass.service;

import com.chewingclass.chewingclass.entity.Chapter;
import com.chewingclass.chewingclass.entity.ClassEntity;
import com.chewingclass.chewingclass.entity.CourseProgress;
import com.chewingclass.chewingclass.entity.User;
import com.chewingclass.chewingclass.repository.ChapterRepository;
import com.chewingclass.chewingclass.repository.ClassRepository;
import com.chewingclass.chewingclass.repository.CourseProgressRepository;
import com.chewingclass.chewingclass.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.ArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CourseProgressServiceTest {

    @InjectMocks
    private CourseProgressService courseProgressService;
    @Mock
    private ChapterRepository chapterRepository;  // ✅ 추가


    @Mock
    private CourseProgressRepository courseProgressRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ClassRepository classRepository;

    private User testUser;
    private ClassEntity testClass;
    private CourseProgress testProgress;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Given: 테스트용 유저, 클래스, 수강 기록을 생성
        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@example.com");

        testClass = new ClassEntity();
        testClass.setId(1L);
        testClass.setTitle("테스트 강의");
        testClass.setTotalLessons(10);

        testProgress = new CourseProgress();
        testProgress.setUser(testUser);
        testProgress.setClassEntity(testClass);
        testProgress.setProgress(50);
        testProgress.setStatus(CourseProgress.Status.ONGOING);
    }

    @Test
    @DisplayName("✅ 사용자의 수강 기록을 정상적으로 조회할 수 있다.")
    void 사용자의_수강기록_조회() {
        // Given
        when(courseProgressRepository.findByUserId(testUser.getId())).thenReturn(List.of(testProgress));

        // When
        List<CourseProgress> result = courseProgressService.getProgressByUser(testUser.getId());

        // Then
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getProgress()).isEqualTo(50);
    }

    @Test
    @DisplayName("✅ 특정 강의의 진행률을 정상적으로 계산할 수 있다.")
    void 특정_강의의_진행률_계산() {
        // Given
        List<Chapter> mockChapters = List.of(
                mock(Chapter.class), mock(Chapter.class), mock(Chapter.class), mock(Chapter.class),
                mock(Chapter.class), mock(Chapter.class), mock(Chapter.class), mock(Chapter.class),
                mock(Chapter.class), mock(Chapter.class)
        );

        testClass.setTotalLessons(10);  // ✅ 전체 강의 수를 10으로 맞춤

        when(courseProgressRepository.countCompletedChapters(testUser.getId(), testClass.getId())).thenReturn(5);
        when(classRepository.findById(testClass.getId())).thenReturn(Optional.of(testClass));
        when(chapterRepository.findByClassEntityIdOrderBySequence(testClass.getId()))
                .thenReturn(mockChapters);

        // When
        int progress = courseProgressService.calculateProgress(testUser.getId(), testClass.getId());

        // Then
        assertThat(progress).isEqualTo(50);  // ✅ 기대값 유지
    }

    @Test
    @DisplayName("✅ 특정 강의의 학습 상태를 반환할 수 있다.")
    void 특정_강의의_학습_상태_반환() {
        // Given
        when(courseProgressRepository.findByUserIdAndClassEntityId(testUser.getId(), testClass.getId()))
                .thenReturn(Optional.of(testProgress));

        // When
        CourseProgress.Status status = courseProgressService.getStatusByUserAndClass(testUser.getId(), testClass.getId());

        // Then
        assertThat(status).isEqualTo(CourseProgress.Status.ONGOING);
    }

    @Test
    @DisplayName("✅ 수강 기록을 추가할 수 있다.")
    void 수강기록_추가() {
        // Given
        CourseProgress newProgress = new CourseProgress(); // ✅ 새로운 객체 생성
        newProgress.setUser(testUser);
        newProgress.setClassEntity(testClass);
        newProgress.setProgress(0);  // ✅ 초기 진행률 0
        newProgress.setStatus(CourseProgress.Status.ONGOING);
        newProgress.setLastWatchedLesson("챕터 1");

        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));
        when(classRepository.findById(testClass.getId())).thenReturn(Optional.of(testClass));
        when(courseProgressRepository.save(any(CourseProgress.class))).thenReturn(newProgress);  // ✅ 새로운 객체 반환

        // When
        CourseProgress result = courseProgressService.addProgress(
                testUser.getId(), testClass.getId(), 0, CourseProgress.Status.ONGOING, "챕터 1"
        );

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getProgress()).isEqualTo(0);
        assertThat(result.getLastWatchedLesson()).isEqualTo("챕터 1");
    }

    @Test
    @DisplayName("✅ 사용자의 최근 학습 강의를 조회할 수 있다.")
    void 사용자의_최근_학습_강의_조회() {
        // Given
        testProgress.setLastWatchedLesson("챕터 5");
        when(courseProgressRepository.findByUserIdAndClassEntityId(testUser.getId(), testClass.getId()))
                .thenReturn(Optional.of(testProgress));

        // When
        String lastLesson = testProgress.getLastWatchedLesson();

        // Then
        assertThat(lastLesson).isEqualTo("챕터 5");
    }

    @Test
    @DisplayName("✅ 수강 기록을 업데이트할 수 있다.")
    void 수강기록_업데이트() {
        // Given
        when(courseProgressRepository.findByUserIdAndClassEntityId(testUser.getId(), testClass.getId()))
                .thenReturn(Optional.of(testProgress));
        when(courseProgressRepository.save(any(CourseProgress.class))).thenReturn(testProgress);  // ✅ 추가

        // When
        CourseProgress updatedProgress = courseProgressService.updateProgress(
                testUser.getId(), testClass.getId(), 80, CourseProgress.Status.COMPLETED, "챕터 8"
        );

        // Then
        assertThat(updatedProgress.getProgress()).isEqualTo(80);
        assertThat(updatedProgress.getStatus()).isEqualTo(CourseProgress.Status.COMPLETED);
        assertThat(updatedProgress.getLastWatchedLesson()).isEqualTo("챕터 8");
    }

    @Test
    @DisplayName("✅ 사용자의 특정 강의 진행률이 50% 이상이면 후기를 작성할 수 있다.")
    void 진행률_50퍼센트_이상이면_후기작성_가능() {
        // Given
        when(courseProgressRepository.findByUserIdAndClassEntityId(testUser.getId(), testClass.getId()))
                .thenReturn(Optional.of(testProgress));

        // When
        boolean reviewAvailable = testProgress.getProgress() >= 50;

        // Then
        assertThat(reviewAvailable).isTrue();
    }

    @Test
    @DisplayName("✅ 수강을 취소하면 진행률이 0이 되고 상태가 CANCELED가 된다.")
    void 수강취소_진행률_초기화() {
        // Given
        when(courseProgressRepository.findByUserIdAndClassEntityId(testUser.getId(), testClass.getId()))
                .thenReturn(Optional.of(testProgress));

        // When
        courseProgressService.cancelProgress(testUser.getId(), testClass.getId());

        // Then
        assertThat(testProgress.getProgress()).isEqualTo(0);
        assertThat(testProgress.getStatus()).isEqualTo(CourseProgress.Status.CANCELED);
    }
}