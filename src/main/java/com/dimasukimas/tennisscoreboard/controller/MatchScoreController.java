package com.dimasukimas.tennisscoreboard.controller;

import com.dimasukimas.tennisscoreboard.model.dto.MatchScoreResponseDto;
import com.dimasukimas.tennisscoreboard.service.MatchScoreCalculationService;
import com.dimasukimas.tennisscoreboard.service.OngoingMatchesService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScoreController extends HttpServlet {

    private final OngoingMatchesService ongoingMatchesService = OngoingMatchesService.getInstance ();
    private final MatchScoreCalculationService matchScoreCalculationService = MatchScoreCalculationService.getInstance();

public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    response.setContentType("text/html; charset=UTF-8");
    response.setCharacterEncoding("UTF-8");
    String matchUuid = request.getParameter("uuid");

    MatchScoreResponseDto matchScore = ongoingMatchesService.getMatchScore(matchUuid);
    request.setAttribute("match", matchScore);

    request.getRequestDispatcher("match-score.jsp").forward(request, response);

}

public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    String uuid = request.getParameter("uuid");

     UUID matchUuid = UUID.fromString(uuid);
     int winnerId = Integer.parseInt(request.getParameter("playerId"));

    MatchScoreResponseDto updatedMatch = matchScoreCalculationService.updateMatchScoreOrFinishMatch(matchUuid, winnerId);
    request.setAttribute("match", updatedMatch);
    request.getRequestDispatcher("match-score.jsp").forward(request, response);

}


}
