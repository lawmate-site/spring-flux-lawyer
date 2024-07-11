package site.lawmate.lawyer.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.messaging.ChangeStreamRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import site.lawmate.lawyer.domain.model.Notice;
import site.lawmate.lawyer.repository.NoticeRepository;
import site.lawmate.lawyer.service.NoticeService;

@Service
@Slf4j
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
    private final NoticeRepository noticeRepository;

    private final Sinks.Many<Notice> sink = Sinks.many().multicast().onBackpressureBuffer();
    private final Sinks.Many<Notice> lawyerSink = Sinks.many().multicast().onBackpressureBuffer();
    private final Sinks.Many<Notice> userSink = Sinks.many().multicast().onBackpressureBuffer();


    public Mono<Notice> createNoticeModel(Notice notice) {
        return noticeRepository.save(notice)
                .doOnSuccess(savedNotice ->{
                        log.info("NoticeModel created: {}", savedNotice);
                        lawyerSink.tryEmitNext(savedNotice);
                });
    }

    public Mono<Notice> updateNoticeModelStatus(String id, String status) {
        return noticeRepository.findById(id)
                .flatMap(notice -> {
                    notice.setStatus(status);
                    return noticeRepository.save(notice)
                            .doOnSuccess(userSink::tryEmitNext);
                });
    }

    public Flux<Notice> getLawyerNoticeModels() {
        return lawyerSink.asFlux();
    }

    public Flux<Notice> getUserNoticeModels(String userId) {
        return userSink.asFlux().filter(notice -> notice.getUserId().equals(userId));
    }

    public Flux<Notice> getNoticesByLawyerId(String lawyerId) {
        return noticeRepository.findAll().filter(notification -> notification.getLawyerId().equals(lawyerId));
    }

    public Mono<Void> deleteNotice(String id) {
        return noticeRepository.deleteById(id);
    }

    public Mono<Void> deleteAllNotices() {
        return noticeRepository.deleteAll();
    }
}
