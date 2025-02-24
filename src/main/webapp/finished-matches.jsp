<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tennis Scoreboard | Finished Matches</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">

    <script src="js/app.js"></script>
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
        <h1>Matches</h1>
        <div class="input-container">
            <input id="playerFilterInput" class="input-filter" placeholder="Filter by name" type="text" value="${filterByPlayerName}" />
            <div>
                    <button id="resetFilterButton" class="btn-filter">Reset Filter</button>
            </div>
        </div>

        <table class="table-matches">
            <tr>
                <th>Player One</th>
                <th>Player Two</th>
                <th>Winner</th>
            </tr>
            <c:choose>
                <c:when test="${empty matches}">
                    <tr>
                        <td colspan="3">No matches found</td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <c:forEach var="match" items="${matches}">
                        <tr>
                            <td>${match.player1Name()}</td>
                            <td>${match.player2Name()}</td>
                            <td>${match.winnerName()}</td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </table>

        <div class="pagination">

            <c:set var="filterQuery" value="${empty param.filter_by_player_name ? '' : '&filter_by_player_name='}${param.filter_by_player_name}" />

            <c:choose>
                <c:when test="${currentPage > 1}">
                    <a class="prev" href="/matches?page=${currentPage - 1}${filterQuery}"> &lt; </a>
                </c:when>
                <c:otherwise>
                    <span class="prev disabled"> &lt; </span>
                </c:otherwise>
            </c:choose>

            <!-- Вычисление начальной и конечной страниц -->
            <c:set var="startPage" value="${currentPage > 1 ? currentPage - 1 : 1}" />
            <c:set var="endPage" value="${startPage + 2}" />
            <c:if test="${endPage > totalPages}">
                <c:set var="endPage" value="${totalPages}" />
            </c:if>

            <!-- Генерация ссылок -->
            <c:forEach var="i" begin="${startPage}" end="${endPage}">
                <c:choose>
                    <c:when test="${i == currentPage}">
                        <span class="num-page current">${i}</span>
                    </c:when>
                    <c:otherwise>
                        <a class="num-page" href="/matches?page=${i}${filterQuery}">${i} </a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <!-- Кнопка "Вперед", отключается на последней странице -->
            <c:choose>
                <c:when test="${currentPage < totalPages}">
                    <a class="next" href="/matches?page=${currentPage + 1}${filterQuery}"> &gt; </a>
                </c:when>
                <c:otherwise>
                    <span class="next disabled"> &gt; </span>
                </c:otherwise>
            </c:choose>
        </div>
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
