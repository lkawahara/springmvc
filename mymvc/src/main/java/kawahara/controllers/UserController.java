package kawahara.controllers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import kawahara.models.UserModel;
import kawahara.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	public UserController() {

	}

	public UserController(UserService userService) {
		this.userService = userService;
	}

	private void addUsers() {
		// add users
		UserModel patrick = new UserModel("patrick", this.getClass()
				.getClassLoader()
				.getResourceAsStream("/kawahara/files/patrick.png"));
		UserModel bob = new UserModel("bob", this.getClass().getClassLoader()
				.getResourceAsStream("/kawahara/files/bob.png"));
		UserModel squid = new UserModel("squid", this.getClass()
				.getClassLoader()
				.getResourceAsStream("/kawahara/files/squid.png"));
		UserModel krabs = new UserModel("krabs", this.getClass()
				.getClassLoader()
				.getResourceAsStream("/kawahara/files/krabs.png"));
		UserModel sandy = new UserModel("sandy", this.getClass()
				.getClassLoader()
				.getResourceAsStream("/kawahara/files/sandy.png"));
		userService.add(patrick);
		userService.add(bob);
		userService.add(squid);
		userService.add(krabs);
		userService.add(sandy);
	}

	@RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
	private void getUserImage(@PathVariable("username") String username,
			HttpServletResponse response) {
		if (this.userService != null) {
			if (this.userService.isEmpty()) {
				//addUsers();
			}
		}
		UserModel possibleUser = userService.get(username);
		InputStream input = null;
		OutputStream output = null;
		try {
			
			if (possibleUser != null) {
				input = new BufferedInputStream(new ByteArrayInputStream(possibleUser.getImgBytes()));
				output = new BufferedOutputStream(response.getOutputStream());
				
				response.reset();
				response.setContentType("image/jpeg");
				response.setHeader("Content-Length",
						String.valueOf(possibleUser.getImgLength()));
			    input = new BufferedInputStream(new ByteArrayInputStream(possibleUser.getImgBytes()));
			    byte[] buffer = new byte[8192];
			    for (int length = 0; (length = input.read(buffer)) > 0; ) {
			        output.write(buffer, 0, length);
			    }
				
			} else {
				response.getWriter().println("user not found");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
		    if (output != null) try { output.close(); } catch (IOException logOrIgnore) {}
		    if (input != null) try { input.close(); } catch (IOException logOrIgnore) {}
		}
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
		System.out.println("FILLING DB IN setUserService");
		System.out.println("FILLING DB IN setUserService");
		System.out.println("FILLING DB IN setUserService");
		System.out.println("FILLING DB IN setUserService");
		System.out.println("FILLING DB IN setUserService");
		System.out.println("FILLING DB IN setUserService");
		System.out.println("FILLING DB IN setUserService");
		System.out.println("FILLING DB IN setUserService");
		//addUsers();
	}

	// public void setRequest(HttpServletRequest request){
	// this.request = request;
	// }
}