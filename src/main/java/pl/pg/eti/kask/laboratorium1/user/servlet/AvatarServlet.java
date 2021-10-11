package pl.pg.eti.kask.laboratorium1.user.servlet;

import pl.pg.eti.kask.laboratorium1.servlet.MimeTypes;
import pl.pg.eti.kask.laboratorium1.servlet.ServletUtility;
import pl.pg.eti.kask.laboratorium1.user.entity.User;
import pl.pg.eti.kask.laboratorium1.user.service.UserService;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.ws.rs.core.HttpHeaders;
import java.io.*;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Optional;

@WebServlet(urlPatterns = AvatarServlet.Paths.USER_AVATAR + "/*")
@MultipartConfig(maxFileSize = 200 * 1024)
public class AvatarServlet extends HttpServlet {

    private UserService userService;

    @Inject
    public AvatarServlet(UserService userService) {
        this.userService = userService;
    }

    public static class Paths {

        public static final String USER_AVATAR = "/api/avatar";
    }

    public static class Patterns {

        public static final String USER = "^/[0-9]+/?$";

    }

    public static class Parameters {

        public static final String AVATAR = "avatar";

    }

    private void getAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException{
        Long id = Long.parseLong(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        Optional<User> user = userService.find(id);
        if(user.isPresent() && !Objects.equals(user.get().getAvatarPath(), "")){
            String filePath = user.get().getAvatarPath();
            File downloadFile = new File(filePath);
            FileInputStream inStream = new FileInputStream(downloadFile);
            response.addHeader(HttpHeaders.CONTENT_TYPE, MimeTypes.IMAGE_PNG);
            response.setContentLength((int)downloadFile.length());
            OutputStream outStream = response.getOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            inStream.close();
            outStream.close();
        } else{
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void postAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Long id = Long.parseLong(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        Optional<User> user = userService.find(id);
        if(user.isPresent() && Objects.equals(user.get().getAvatarPath(), "")){
            Part avatar = request.getPart(Parameters.AVATAR);
            String fileName = java.nio.file.Paths.get(avatar.getSubmittedFileName()).getFileName().toString();
            InputStream inputStream = avatar.getInputStream();
            saveAvatarFile("D:\\avatars-przeslane\\" + fileName, inputStream);
            user.get().setAvatarPath("D:\\avatars-przeslane\\" + fileName);
            userService.update(user.get());

        }
    }

    private void saveAvatarFile(String filePath , InputStream inputStream) throws IOException{
        File file = new File(filePath);
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            int read;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        }
    }

    private void putAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Long id = Long.parseLong(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        Optional<User> user = userService.find(id);
        if(user.isPresent() && !Objects.equals(user.get().getAvatarPath(), "")) {
            Part avatar = request.getPart(Parameters.AVATAR);
            String fileName = java.nio.file.Paths.get(avatar.getSubmittedFileName()).getFileName().toString();
            InputStream inputStream = avatar.getInputStream();
            saveAvatarFile("D:\\avatars-przeslane\\" + fileName, inputStream);
            String filePath = user.get().getAvatarPath();
            File fileOld = new File(filePath);
            fileOld.delete();
            user.get().setAvatarPath("D:\\avatars-przeslane\\" + fileName);
            userService.update(user.get());
        }
    }


    private void deleteAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Long id = Long.parseLong(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        Optional<User> user = userService.find(id);
        if(user.isPresent() && !Objects.equals(user.get().getAvatarPath(), "")) {
            String filePath = user.get().getAvatarPath();
            File file = new File(filePath);
            file.delete();
            user.get().setAvatarPath("");
            userService.update(user.get());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.USER_AVATAR.equals(servletPath)) {
            if (path.matches(Patterns.USER)) {
                getAvatar(request, response);
                response.sendError(HttpServletResponse.SC_OK);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.USER_AVATAR.equals(servletPath)) {
            if (path.matches(Patterns.USER)) {
                postAvatar(request, response);
                response.sendError(HttpServletResponse.SC_CREATED);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(request);
        if (Paths.USER_AVATAR.equals(request.getServletPath())) {
            if (path.matches(Patterns.USER)) {
                putAvatar(request, response);
                response.sendError(HttpServletResponse.SC_OK);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.USER_AVATAR.equals(servletPath)) {
            if (path.matches(Patterns.USER)) {
                deleteAvatar(request, response);
                response.sendError(HttpServletResponse.SC_ACCEPTED);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }
}
