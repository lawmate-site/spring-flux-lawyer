package site.lawmate.lawyer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import site.lawmate.lawyer.domain.model.LawyerModel;
import site.lawmate.lawyer.domain.model.ReplyModel;
import site.lawmate.lawyer.repository.LawyerRepository;
import site.lawmate.lawyer.repository.ReplyRepository;
import site.lawmate.lawyer.service.ReplyService;

@RequiredArgsConstructor
@Service
public class ReplyServiceImpl implements ReplyService {
    private final ReplyRepository replyRepository;
    private final LawyerRepository lawyerRepository;

    public Mono<LawyerModel> replyToLawyer(String id, ReplyModel reply) {
        return lawyerRepository.findById(id)
                .flatMap(lawyer -> {
                    reply.setLawyerId(id); // ReplyModel에 변호사 ID 설정
                    return replyRepository.save(reply)
                            .flatMap(savedReply -> {
                                // 기존 replys에 savedReply를 추가하는 로직
                                Flux<ReplyModel> updatedReplies = lawyer.getReplies().concatWith(Flux.just(savedReply));
                                lawyer.setReplies(updatedReplies);
                                return lawyerRepository.save(lawyer);
                            });
                });
    }

    public Mono<ReplyModel> updateReply(String id, ReplyModel reply) {
        return replyRepository.findById(id)
                .flatMap(i->{
                    i.setContent(reply.getContent());
                    return replyRepository.save(i);
                });
    }

    public Flux<ReplyModel> getAllReplies() {
        return replyRepository.findAll();
    }


    public Mono<Void> deleteReply(String id) {
        return replyRepository.deleteById(id);
    }

    public Flux<ReplyModel> getRepliesByLawyerId(String lawyerId) {
        return replyRepository.findAllByLawyerId(lawyerId);
    }
}

