export async function getAllPosts() {
  const response = await fetch("http://localhost:8080/posts");
  // check if response is ok
  if (!response.ok) {
    throw new Error("Something went wrong!");
  }

  const data = await response.json();
  return data;
}

export async function getPostData(id) {
  const response = await fetch(`http://localhost:8080/posts/${id}`);
  if (!response.ok) {
    throw new Error("Something went wrong!");
  }
  const data = await response.json();
  return data;
}

export async function getAllComments() {
  const response = await fetch("http://localhost:8080/comments");
  if (!response.ok) {
    throw new Error("Something went wrong!");
  }
  const data = await response.json();
  return data;
}
