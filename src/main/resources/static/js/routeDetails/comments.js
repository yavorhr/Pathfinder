document.addEventListener('DOMContentLoaded', function () {
    const container = document.getElementById('commentCtnr');
    const routeId = document.getElementById("route_id").value;

    // Fetch all comments
    const allComments = []

    fetch(`http://localhost:8080/api/${routeId}/comments`)
        .then(response => response.json())
        .then(data => {
            for (let comment of data) {
                allComments.push(comment)
            }

            console.log(allComments)
            displayComments(allComments)
        });

    const displayComments = (comments) => {
        container.innerHTML = comments.map(
            (c) => {
                return asComment(c)
            }
        ).join('')
    }

    function asComment(c) {
        return `<li id="comment-${c.commentId}">
            <h4 class="author" >
                ${c.author}
                <span class="timestamp" >(${c.created})</span>
            </h4>
            <p >
                ${c.textContent}
            </p>
        </li>`
    }
});

// Post a comment