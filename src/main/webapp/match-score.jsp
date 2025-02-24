<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tennis Scoreboard | Match Score</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;700&display=swap" rel="stylesheet">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto+Mono:wght@300&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">

    <script src="js/app.js"></script>

    <script>

        const urlParams = new URLSearchParams(window.location.search);
        const uuid = urlParams.get("uuid");

        if (uuid) {
            document.getElementById("player1ScoreForm").action = "/match-score?uuid=" + encodeURIComponent(uuid);
            document.getElementById("player2ScoreForm").action = "/match-score?uuid=" + encodeURIComponent(uuid);
        }
    </script>
</head>
<body>
<header class="header">
    <section class="nav-header">
        <div class="brand">
            <div class="nav-toggle">
                <img src="images/menu.png" alt="Logo" class="logo">
            </div>
            <span class="logo-text">TennisScoreboard</span>
        </div>
        <div>
            <nav class="nav-links">
                <a class="nav-link" href="/">Home</a>
                <a class="nav-link" href="/matches?page=1">Matches</a>
            </nav>
        </div>
    </section>
</header>
<main>
    <div class="container">
        <h1>Current match</h1>
        <div class="current-match-image"></div>
        <section class="score">
            <table class="table">
                <thead class="result">
                <tr>
                    <th class="table-text">Player</th>
                    <th class="table-text">Sets</th>
                    <th class="table-text">Games</th>
                    <th class="table-text">Points</th>
                </tr>
                </thead>
                <tbody>
                <tr class="player1">
                    <td class="table-text">${match.player1Name()}</td>
                    <td class="table-text">${match.player1Sets()}</td>
                    <td class="table-text">${match.player1Games()}</td>
                    <td class="table-text">${match.player1Points()}</td>
                    <td class="table-text">
                        <c:choose>
                        <c:when test="${match.winnerId() eq match.player1Id()}">
                    <span class="winner-text">🏆 Winner</span>
                    </c:when>
                    <c:when test="${match.winnerId() eq 0}">
                            <form method="post" id="player1ScoreForm">
                                <input type="hidden" name="playerId" value="${match.player1Id()}">
                                <button class="score-btn" type="submit">Score</button>
                            </form>
                    </c:when>
                    <c:otherwise>
                    </c:otherwise>
                    </c:choose>
                    </td>
                </tr>
                <tr class="player2">
                    <td class="table-text">${match.player2Name()}</td>
                    <td class="table-text">${match.player2Sets()}</td>
                    <td class="table-text">${match.player2Games()}</td>
                    <td class="table-text">${match.player2Points()}</td>
                    <td class="table-text">
                    <c:choose>
                    <c:when test="${match.winnerId() eq match.player2Id()}">
                        <span class="winner-text">🏆 Winner</span>
                        </c:when>
                         <c:when test="${match.winnerId() eq 0}">
                        <form method="post" id="player2ScoreForm">
                            <input type="hidden" name="playerId" value="${match.player2Id()}">
                            <button class="score-btn" type="submit">Score</button>
                        </form>
                         </c:when>
                    <c:otherwise>
                    </c:otherwise>
                    </c:choose>
                    </td>
                </tr>
                </tbody>
            </table>
        </section>
    </div>
</main>
<footer>
    <div class="footer">
        <p>&copy; Tennis Scoreboard, project from <a href="https://zhukovsd.github.io/java-backend-learning-course/">zhukovsd/java-backend-learning-course</a>
            roadmap.</p>
    </div>
</footer>
</body>
</html>
