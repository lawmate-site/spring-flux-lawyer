package site.lawmate.lawyer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import site.lawmate.lawyer.domain.model.ReplyModel;
import site.lawmate.lawyer.repository.ReplyRepository;
import site.lawmate.lawyer.service.ReplyService;

@RequiredArgsConstructor
@Service
public class ReplyServiceImpl implements ReplyService {
    private final ReplyRepository replyRepository;

    public Mono<ReplyModel> createReply(String lawyerId, ReplyModel reply) {
        reply.setLawyerId(lawyerId);
        reply.setQuestionId("qId");
        return replyRepository.save(reply);
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
        return replyRepository.findByLawyerId(lawyerId);
    }
}
