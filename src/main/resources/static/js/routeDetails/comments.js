document.addEventListener('DOMContentLoaded', function () {
    const container = document.getElementById('commentCtnr');
    const routeId = document.getElementById("route_id").value;

    const commentForm = document.getElementById('commentForm')
    commentForm.addEventListener("submit", handleCommentSubmit)

    const messageElement = document.getElementById("message");

    const csrfHeaderName = document.head.querySelector('[name="_csrf_header"]').content;
    const csrfHeaderValue = document.head.querySelector('[name="_csrf"]').content;

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

    // Post a comment
    async function handleCommentSubmit(event) {
        event.preventDefault();

        const form = event.currentTarget;
        const url = form.action;
        const formData = new FormData(form);

        try {
            const responseData = await postFormDataAsJson({url, formData});

            container.insertAdjacentHTML("afterbegin", asComment(responseData));

            if (messageElement && messageElement.classList.contains("is-invalid")) {
                messageElement.classList.remove("is-invalid");
            }

            form.reset();
        } catch (error) {
            let errorObj = JSON.parse(error.message);

            if (errorObj.fieldWithErrors) {
                errorObj.fieldWithErrors.forEach(
                    e => {
                        let elementWithError = document.getElementById(e);
                        if (elementWithError) {
                            elementWithError.classList.add("is-invalid");
                        }
                    });
            }
        }
        console.log('going to submit a comment!')
    }

    async function postFormDataAsJson({url, formData}) {

        const plainFormData = Object.fromEntries(formData.entries());
        const formDataAsJSONString = JSON.stringify(plainFormData);

        const fetchOptions = {
            method: "POST",
            headers: {
                [csrfHeaderName]: csrfHeaderValue,
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            body: formDataAsJSONString
        }

        const response = await fetch(url, fetchOptions);

        if (!response.ok) {
            const errorMessage = await response.text();
            throw new Error(errorMessage);
        }

        return response.json();
    }
});

