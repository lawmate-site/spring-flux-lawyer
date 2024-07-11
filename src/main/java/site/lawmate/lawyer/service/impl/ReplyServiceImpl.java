package site.lawmate.lawyer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import site.lawmate.lawyer.domain.model.Lawyer;
import site.lawmate.lawyer.domain.model.Reply;
import site.lawmate.lawyer.repository.LawyerRepository;
import site.lawmate.lawyer.repository.ReplyRepository;
import site.lawmate.lawyer.service.ReplyService;

@RequiredArgsConstructor
@Service
public class ReplyServiceImpl implements ReplyService {
    private final ReplyRepository replyRepository;
    private final LawyerRepository lawyerRepository;

    public Mono<Lawyer> replyToLawyer(String id, String articleId, Reply reply) {
        return lawyerRepository.findById(id)
                .flatMap(lawyer -> {
                    reply.setArticleId(articleId);
                    reply.setLawyerId(lawyer.getId());
                    return replyRepository.save(reply)
                            .then(Mono.just(lawyer));
                });
    }

    public Mono<Reply> updateReply(String id, Reply replyModel) {
        return replyRepository.findById(id)
                .flatMap(reply->{
                    reply.setContent(replyModel.getContent());
                    return replyRepository.save(reply);
                });
    }

    public Flux<Reply> getAllReplies() {
        return replyRepository.findAll();
    }


    public Mono<Void> deleteReply(String id) {
        return replyRepository.deleteById(id);
    }

    public Flux<Reply> getRepliesByLawyerId(String lawyerId) {
        return replyRepository.findAllByLawyerId(lawyerId);
    }

    public Mono<Void> deleteAllReplies() {
        return replyRepository.deleteAll();
    }
}

