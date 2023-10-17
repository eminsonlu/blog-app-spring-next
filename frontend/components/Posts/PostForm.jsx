import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";

import { useState } from "react";

export default function PostForm({ refreshData, userId }) {

  const [title, setTitle] = useState("");
  const [text, setText] = useState("");

  const handleSubmit = () => {
    if (text.length === 0 || title.length === 0) return;

    const data = {
      title,
      text,
      userId: userId,
    };

    fetch("/api/post/makePostRequest", {
      method: "POST",
      body: JSON.stringify(data),
      headers: {
        "Content-Type": "application/json",
      },
    }).then((res) => {
      if (res.ok) {
        refreshData();
        setTitle("");
        setText("");
      } else {
        console.log(res);
      }
    });
  };

  return (
    <div className="flex flex-col gap-2 p-4">
      <p className="text-xl">Create a post</p>
      <Input
        value={title}
        onChange={(e) => setTitle(e.target.value)}
        placeholder="Title"
      />
      <Input
        value={text}
        onChange={(e) => setText(e.target.value)}
        placeholder="Text"
      />
      <Button onClick={() => handleSubmit()}>Submit</Button>
    </div>
  );
}
