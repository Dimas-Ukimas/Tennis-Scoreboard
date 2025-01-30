package com.dimasukimas.tennisscoreboard.controller;

import com.dimasukimas.tennisscoreboard.repository.PlayerRepository;
import com.dimasukimas.tennisscoreboard.service.OngoingMatchesService;
import com.dimasukimas.tennisscoreboard.util.HibernateUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/new-match")
public class NewMatchController extends HttpServlet {

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    PlayerRepository playerRepository = new PlayerRepository();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String player1Name = request.getParameter("player1Name");
        String player2Name = request.getParameter("player2Name");

        UUID newMatchUuid = OngoingMatchesService.getInstance(playerRepository, sessionFactory).createNewMatch(player1Name, player2Name);

        String redirectUrl = "/match-score?uuid=" + newMatchUuid;
        response.sendRedirect(redirectUrl);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.getRequestDispatcher("new-match.jsp").forward(request, response);

    }


}
