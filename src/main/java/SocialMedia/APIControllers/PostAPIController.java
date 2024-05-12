package SocialMedia.APIControllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import SocialMedia.Auth.Registration.RegisterRequest;
import SocialMedia.Entities.Comment;
import SocialMedia.Entities.Mode;
import SocialMedia.Entities.Post;
import SocialMedia.Models.CommentModel;
import SocialMedia.Models.PostModel;
import SocialMedia.Response.Response;
import SocialMedia.Services.IPostService;
import hcmute.alohcmute.entities.BaiViet;
import hcmute.alohcmute.entities.CheDo;
import hcmute.alohcmute.entities.Nhom;
import hcmute.alohcmute.entities.TaiKhoan;
import hcmute.alohcmute.security.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import vn.iotstar.entity.Product;

@RestController
@RequestMapping("/api/post")
public class PostAPIController {
	@Autowired
	IPostService postService;
	
	@GetMapping("/{username}")
	public ResponseEntity<?> getPostWithUsername(
			@PathVariable(value = "username") String username, 
			HttpServletRequest request) {
		
		List<Post> posts = postService.findAllPosts(username);
		List<PostModel> postModels = new ArrayList<>();
		
		for (Post post : posts) {
			PostModel postModel = new PostModel();
			postModel.setAvatar(post.getPosterAccount().getAvatarURL());
			postModel.setUsername(post.getPosterAccount().getUsername());
			postModel.setFullName(post.getPosterAccount().getFullname());
			postModel.setPostingTimeAt(post.getPostTimeAt().toString());
			postModel.setMode(post.getMode().getModeId());
			postModel.setPostText(post.getText());
			postModel.setPostMedia(post.getMediaURL());
			postModel.setLiked(false);
			postModels.add(postModel);
		}
		
		return new ResponseEntity<Response>(
				new Response(true, "Thành công", postModels), 
				HttpStatus.OK
		);
	}
    private String avatar;
    private String username;
    private String fullName;
    
    private String postingTimeAt;
    private long mode;
    private String postText;
    private String postMedia;
    private boolean liked;
    
    @PostMapping(path = "/post/addPost")
	public ResponseEntity<?> CreatePost(@RequestBody PostModel postModel)
	{
		Post post = new Post();
		if (postModel.getPostText().equals("")&&postModel.getPostMedia().equals("")) {
			return new ResponseEntity<Response>(new Response(false, "Thêm thất bại", postModel), HttpStatus.BAD_REQUEST);
		} 
		else
		{
			Long modeId = postModel.getMode();
			Mode mode = 
			TaiKhoan taikhoan= taikhoanSer.findBytaiKhoan(postModel.getUsername());
			baiviet.setNoiDungChu(noidungchu+color);
			String linkanh= filename;
			baiviet.setNoiDungHinhAnh(linkanh);
			baiviet.setTaiKhoan(taikhoan);
			System.out.println(taikhoan.getHoTen());
			System.out.println(cdo);
			if(cdo.equals("Public"))
			{
				tenchedo="Công Khai";
			}
			else if(cdo.equals("Follower"))
			{
				tenchedo="Người Theo Dõi";
			}
			else if(cdo.equals("Private"))
			{
				tenchedo="Riêng Tư";
			}
			if (!manhom.equals(""))
			{
				int grID = Integer.parseInt(manhom);
				Nhom nhom = nhomSer.findBymaNhom(grID);
				baiviet.setNhom(nhom);
				CheDo chedo=chedoSer.findByID(4).get();
				baiviet.setCheDoNhom(chedo);
			}
			else {
				CheDo chedo=chedoSer.findByCheDo(tenchedo);
				baiviet.setCheDoNhom(chedo);
			}
			baiviet.setNgay(LocalDate.now());
			baiviet.setEnable(true);
			baiviet.setThoiGian(LocalTime.now());
			baivietSer.save(baiviet);
		}
		
		
		productService.save(product);
		// return ResponseEntity.ok().body(category);
		return new ResponseEntity<Response>(new Response(true, "Thêm Thành công", product), HttpStatus.OK);
	}
	
	
}
