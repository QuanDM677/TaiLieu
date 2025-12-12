<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Academic Share</a>

        <div class="ms-auto">
            <a href="locale?lang=en"
               class="btn btn-light btn-sm ${sessionScope.locale == 'en_US' ? 'active' : ''}">
                EN
            </a>

            <a href="locale?lang=vi"
               class="btn btn-light btn-sm ${sessionScope.locale == 'vi_VN' ? 'active' : ''}">
                VN
            </a>
        </div>
    </div>
</nav>
