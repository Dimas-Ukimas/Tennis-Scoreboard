package com.dimasukimas.tennisscoreboard.controller;

import com.dimasukimas.tennisscoreboard.dto.PaginatedMatchesResponseDto;
import com.dimasukimas.tennisscoreboard.service.FinishedMatchesPersistenceService;
import com.dimasukimas.tennisscoreboard.util.DataValidationUtil;
import com.dimasukimas.tennisscoreboard.util.PropertiesUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/matches")
public class FinishedMatchesController extends HttpServlet {

    private final FinishedMatchesPersistenceService finishedMatchesPersistenceService = FinishedMatchesPersistenceService.getInstance();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String pageNumber = request.getParameter("page");
        String playerName = request.getParameter("filter_by_player_name");

        int page = DataValidationUtil.parsePageNumber(pageNumber);
        int pageSize = Integer.parseInt(PropertiesUtil.get("page.size"));

        PaginatedMatchesResponseDto finishedMatchesPage =
                playerName == null
                        ? finishedMatchesPersistenceService.getAllPaginatedMatches(page, pageSize)
                        : finishedMatchesPersistenceService.getPlayerPaginatedMatches(page, pageSize, playerName);


        request.setAttribute("matchesPage", finishedMatchesPage);
        request.getRequestDispatcher("WEB-INF/finished-matches.jsp").forward(request, response);
    }

}
