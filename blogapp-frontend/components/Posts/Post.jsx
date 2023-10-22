import Comment from "./Comment";
import Like from "./Like";

const Post = ({ post }) => {
  return (
    <div className="flex flex-col gap-2 p-4">
      <p className="text-xl">
        <span className="text-base text-slate-300">@{post.userName} </span>
        {post.title}
      </p>
      <p className="text-sm text-slate-400">{post.text}</p>
      <Comment postId={post.id} />
      <Like likes={post.likes} postId={post.id} />
    </div>
  );
};

export default Post;
