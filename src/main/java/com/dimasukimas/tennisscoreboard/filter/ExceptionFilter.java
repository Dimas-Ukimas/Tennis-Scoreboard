package com.dimasukimas.tennisscoreboard.filter;

import com.dimasukimas.tennisscoreboard.exception.DataBaseException;
import com.dimasukimas.tennisscoreboard.exception.InvalidDataException;
import com.dimasukimas.tennisscoreboard.exception.NotFoundException;
import com.dimasukimas.tennisscoreboard.exception.PlayerAlreadyExistsException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("*")
public class ExceptionFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        try {
            super.doFilter(req, res, chain);
        } catch (DataBaseException e) {
            printToErrorPage(e, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, req, res);
        } catch (NotFoundException | IllegalArgumentException e) {
            printToErrorPage(e, HttpServletResponse.SC_NOT_FOUND, req, res);
        } catch (InvalidDataException | PlayerAlreadyExistsException e) {
            printToNewMatchPage(e, req, res);
        } catch (Exception e) {
            printToErrorPage(e, HttpServletResponse.SC_SERVICE_UNAVAILABLE, req, res);
        }
    }

    private void printToErrorPage(Exception e, int code, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String errorMessage = e.getMessage();

        req.setAttribute("errorMessage", errorMessage);
        req.setAttribute("status_code", code);
        req.getRequestDispatcher("WEB-INF/error-page.jsp").forward(req, resp);
    }

    private void printToNewMatchPage(Exception e, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String errorMessage = e.getMessage();

        req.setAttribute("errorMessage", errorMessage);
        req.getRequestDispatcher("WEB-INF/new-match.jsp").forward(req, resp);
    }


}
