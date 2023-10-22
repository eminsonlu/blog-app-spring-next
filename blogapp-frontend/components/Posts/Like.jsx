import { Button } from "@/components/ui/button";
import { useState, useEffect } from "react";
const fetcher = (url) => fetch(url).then((res) => res.json());
import useSWR from "swr";

export default function Like({ likes, postId }) {
  const [user, setUser] = useState(null);

  const [likeId, setLikeId] = useState(null);

  const [isLiked, setIsLiked] = useState(null);
  const [likeCount, setLikeCount] = useState(likes.length);
  const { data, error } = useSWR("/api/auth/user", fetcher);
  useEffect(() => {
    if (data?.isLoggedIn) {
      setIsLiked(
        likes.some((like) => like.userId === data.id)
      );
      setLikeId(
        likes.find((like) => like.userId === data.id)?.id
      );
      setUser(data);
    }
  }, [data, likes]);
  const handleLike = () => {
    fetch("/api/post/likePost", {
      method: isLiked ? "DELETE" : "POST",
      body: JSON.stringify({
        postId: postId,
        userId: user.id,
        likeId,
      }),
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((res) => {
        return res.json();
      })
      .then((data) => {
        if(data?.statusCode === 500) return
        if (data.id) {
          setLikeId(data.id);
          setIsLiked(true);
          setLikeCount(likeCount + 1);
        } else {
          setLikeId(null);
          setIsLiked(false);
          setLikeCount(likeCount - 1);
        }
      });
  };

  if (error) return <div>failed to load</div>;

  return (
    <Button
      onClick={() => {
        if (user?.isLoggedIn) {
          handleLike();
        } else {
          alert("You must be logged in to like a post");
        }
      }}
      className={`${isLiked && "bg-red-400 hover:bg-red-300"} transition-all`}
    >
      Like {likeCount}
    </Button>
  );
}
