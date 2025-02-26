<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset=UTF-8">

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tennis Scoreboard | Home</title>
    <link rel="preconnect" href="https://fonts.googleapis.com/">
    <link rel="preconnect" href="https://fonts.gstatic.com/" crossorigin="">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">

    <script src="${pageContext.request.contextPath}/js/app.js"></script>
<body>
<header class="header">
    <section class="nav-header">
        <div class="brand">
            <div class="nav-toggle">
                <img src="${pageContext.request.contextPath}/images/menu.png" alt="Logo" class="logo">
            </div>
            <span class="logo-text">TennisScoreboard</span>
        </div>
        <div>
            <nav class="nav-links">
                <a class="nav-link" href="${pageContext.request.contextPath}/">Home</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/matches?page=1">Matches</a>
            </nav>
        </div>
    </section>
</header>
<main>
    <div class="container">
        <h1>Welcome to the Tennis Scoreboard club, buddy!</h1>
        <p>Manage your tennis matches, record results, and track rankings</p>
        <div class="welcome-image"></div>
        <div class="form-container center">

            <form action="/new-match" method="get" style="display: inline;">
                <button class="btn start-match" type="submit">
                    Start a new Match
                </button>
            </form>

            <a class="homepage-action-button" href="${pageContext.request.contextPath}/matches?page=1">
                <button class="btn view-results">
                    View Match results
                </button>
            </a>
        </div>
    </div>
</main>
<footer>
    <div class="footer">
        <p>&copy; Tennis Scoreboard, project from <a href="https://zhukovsd.github.io/java-backend-learning-course/">zhukovsd/java-backend-learning-course</a> roadmap.</p>
    </div>
</footer>


</body><div class="troywell-caa"><template shadowrootmode="open">
    <style>
        @import url("chrome-extension://adlpodnneegcnbophopdmhedicjbcgco/caa/styles.css");
    </style>
    <div id="troywell-caa" data-v-app=""><div data-v-fd23d602="" class="content-app"><!----></div></div></template></div><div class="troywell-avia"><template shadowrootmode="open">
    <style>
        @import url("chrome-extension://adlpodnneegcnbophopdmhedicjbcgco/flight/styles.css");
    </style>
    <div id="troywell-avia" data-v-app=""><div class="app"><div data-v-dd12f266=""><!----></div></div></div></template></div></html>