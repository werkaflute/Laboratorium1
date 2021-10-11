package pl.pg.eti.kask.laboratorium1.user.servlet;

import pl.pg.eti.kask.laboratorium1.servlet.MimeTypes;
import pl.pg.eti.kask.laboratorium1.servlet.ServletUtility;
import pl.pg.eti.kask.laboratorium1.user.dto.GetUserResponse;
import pl.pg.eti.kask.laboratorium1.user.dto.GetUsersResponse;
import pl.pg.eti.kask.laboratorium1.user.entity.User;
import pl.pg.eti.kask.laboratorium1.user.service.UserService;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = UserServlet.Paths.USERS + "/*")
public class UserServlet extends HttpServlet {

    private UserService userService;

    @Inject
    public UserServlet(UserService userService) {
        this.userService = userService;
    }

    private final Jsonb jsonb = JsonbBuilder.create();

    public static class Paths {

        public static final String USERS = "/api/users";


    }

    public static class Patterns {

        public static final String USERS = "^/?$";

        public static final String USER = "^/[0-9]+/?$";

    }

    private void getUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MimeTypes.APPLICATION_JSON);
        response.getWriter()
                .write(jsonb.toJson(GetUsersResponse.entityToDtoMapper().apply(userService.findAll())));
    }

    private void getUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id  = Long.parseLong(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        Optional<User> user = userService.find(id);
        if (user.isPresent()) {
            response.setContentType(MimeTypes.APPLICATION_JSON);
            response.getWriter()
                    .write(jsonb.toJson(GetUserResponse.entityToDtoMapper().apply(user.get())));
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.USERS.equals(servletPath)) {
            if (path.matches(Patterns.USERS)) {
                getUsers(request, response);
                return;
            } else{
                getUser(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }
}
