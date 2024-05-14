package SocialMedia.Services;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SocialMedia.Entities.Comment;
import SocialMedia.Repositories.CommentRepository;
import SocialMedia.Repositories.PostRepository;

@Service
public class CommentServiceImpl implements ICommentService{
	@Autowired
	CommentRepository commentRepository;
	@Autowired
	PostRepository postRepository;
	
	@Override
	public List<Comment> findCommentsByPostId(Long postId) {
		List<Comment> list = commentRepository.findCommentsByPostId(postId);
        List<Comment> listComments = list.stream().sorted((cm1, cm2) -> {
			return cm2.getCommentTimeAt().compareTo(cm1.getCommentTimeAt());
		}).collect(Collectors.toList());
        return listComments;
	}
	@Override
	public Comment getById(Long id) {
		return commentRepository.getById(id);
	}
	@Override
	public <S extends Comment> S save(S entity) {
		return commentRepository.save(entity);
	}
	
	@Override
	public void deleteById(Long id) {
		commentRepository.deleteById(id);
	}
}
