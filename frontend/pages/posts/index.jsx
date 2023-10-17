import { getAllPosts } from "@/helpers/api-util";
import { useRouter } from "next/router";

import Post from "@/components/Posts/Post";
import PostForm from "@/components/Posts/PostForm";

import useSWR from "swr";
import { useState, useEffect } from "react";

const fetcher = (url) => fetch(url).then((res) => res.json());

export default function Page(props) {
  const [user, setUser] = useState(null);
  const [userId, setUserId] = useState(null);

  const { data, error } = useSWR("/api/auth/user", fetcher);
  const router = useRouter();

  const refreshData = () => {
    router.replace(router.asPath);
  };

  useEffect(() => {
    if (data) {
      setUser(data.isLoggedIn);
      setUserId(data.id);
    }
  }, [data]);

  if (error) return <div>failed to load</div>;

  return (
    <div className="flex flex-col gap-4">
      {user && <PostForm refreshData={refreshData} userId={userId} />}
      {props.posts.map((post) => (
        <Post key={post.id} post={post} user={user} />
      ))}
    </div>
  );
}

export async function getServerSideProps() {
  const allPostsData = await getAllPosts();

  return {
    props: {
      posts: allPostsData,
    },
  };
}
