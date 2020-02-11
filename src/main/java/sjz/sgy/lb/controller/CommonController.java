package sjz.sgy.lb.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import sjz.sgy.lb.util.FileTools;
import sjz.sgy.lb.util.R;

/**
 * @description 可以通用的某些接口
 * @author 申光跃
 *
 */
@RestController
public class CommonController {
	
	@RequestMapping("/file")
	public String file() {
		return "file";
	}
	
	/**
	 * @description 单个文件上传
	 * @author 申光跃
	 * @param file 文件
	 * @return
	 */
	@PostMapping("/common/uploadFile")
	@ResponseBody
	public R uoloadFile(MultipartFile file) {
		String fileName = null;
		try {
			fileName = FileTools.uploadFile(file);
		} catch (IOException e) {
			e.printStackTrace();
			return R.error("文件上传失败");
		}
		return R.ok().put("fileName", fileName);
	}
	
}
