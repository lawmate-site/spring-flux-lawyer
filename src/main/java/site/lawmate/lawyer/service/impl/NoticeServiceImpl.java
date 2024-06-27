package site.lawmate.lawyer.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import site.lawmate.lawyer.domain.model.NoticeModel;
import site.lawmate.lawyer.repository.NoticeRepository;
import site.lawmate.lawyer.service.NoticeService;

@Service
@Slf4j
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
    private final NoticeRepository noticeRepository;
    private final Sinks.Many<NoticeModel> lawyerSink = Sinks.many().multicast().onBackpressureBuffer();
    private final Sinks.Many<NoticeModel> userSink = Sinks.many().multicast().onBackpressureBuffer();


    public Mono<NoticeModel> createNoticeModel(NoticeModel notice) {
        return noticeRepository.save(notice)
                .doOnSuccess(savedNoticeModel ->{
                        log.info("NoticeModel created: {}", savedNoticeModel);
                        lawyerSink.tryEmitNext(savedNoticeModel);
                });
    }

    public Mono<NoticeModel> updateNoticeModelStatus(String id, String status) {
        return noticeRepository.findById(id)
                .flatMap(notice -> {
                    notice.setStatus(status);
                    return noticeRepository.save(notice)
                            .doOnSuccess(updatedNoticeModel -> userSink.tryEmitNext(updatedNoticeModel));
                });
    }

    public Flux<NoticeModel> getLawyerNoticeModels() {
        return lawyerSink.asFlux();
    }

    public Flux<NoticeModel> getUserNoticeModels(String userId) {
        return userSink.asFlux().filter(notice -> notice.getUserId().equals(userId));
    }

    public Flux<NoticeModel> getNotificationsByLawyerId(String lawyerId) {
        return noticeRepository.findAll().filter(notification -> notification.getLawyerId().equals(lawyerId));
    }

    public Mono<Void> deleteNotification(String id) {
        return noticeRepository.deleteById(id);
    }
}
