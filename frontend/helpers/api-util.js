export async function getAllPosts() {
    const response = await fetch(
        'http://localhost:8080/posts'
    );
    const data = await response.json();
    return data;
}

export async function getPostData(id) {
    const response = await fetch(
        `http://localhost:8080/posts/${id}`
    );
    const data = await response.json();
    return data;
}

export async function getAllComments() {
    const response = await fetch(
        'http://localhost:8080/comments'
    );
    const data = await response.json();
    return data;
}