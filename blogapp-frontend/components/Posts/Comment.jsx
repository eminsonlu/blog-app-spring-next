import {
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from "@/components/ui/accordion";
import { Button } from "@/components/ui/button";

import useSWR from "swr";

import { useCallback, useEffect, useState } from "react";
const fetcher = (url) => fetch(url).then((res) => res.json());

export default function Comment({ postId }) {
  const [user, setUser] = useState(null);
  const [comments, setComments] = useState([]);
  const [isOpen, setIsOpen] = useState(false);
  const [isLoading, setIsLoading] = useState(false);

  const [comment, setComment] = useState("");

  const { data, error } = useSWR("/api/auth/user", fetcher);

  useEffect(() => {
    if (data) {
      setUser(data);
    }
  }, [data]);

  const getComments = useCallback(() => {
    setIsLoading(true);
    fetch(`/api/post/getCommentWithPostId?postId=` + postId, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((res) => res.json())
      .then((data) => {
        setIsLoading(false);
        if(data?.statusCode === 500) return
        setComments(data);
      });
  }, [postId]);

  useEffect(() => {
    if (isOpen) {
      getComments();
    }
  }, [getComments, isOpen]);

  const handleSubmit = () => {
    fetch("/api/post/makeCommentRequest", {
      method: "POST",
      body: JSON.stringify({
        text: comment,
        postId: postId,
        userId: user.id,
      }),
      headers: {
        "Content-Type": "application/json",
      },
    }).then((res) => {
      if (res.ok) {
        setComment("");
        getComments();
      } else {
        console.log(res);
      }
    });
  };

  if (error) return <div>failed to load</div>;

  return (
    <Accordion collapsible>
      <AccordionItem value="comment">
        <AccordionTrigger
          onClick={() => {
            setIsOpen((old) => !old);
          }}
        >
          Comments
        </AccordionTrigger>
        {isLoading && <p>Loading...</p>}
        {comments?.map((comment) => (
          <AccordionContent key={comment.id}>
            <div className="bg-slate-800 p-3 rounded-lg dark:text-white">
              {comment.text}
            </div>
          </AccordionContent>
        ))}
        {user?.isLoggedIn && (
          <AccordionContent>
            <div className="w-full flex justify-between gap-4">
              <input
                className="bg-slate-800 text-slate-100 p-3 rounded-lg w-full outline-none"
                placeholder="Add a comment"
                value={comment}
                onChange={(e) => setComment(e.target.value)}
              />
              <Button
                className="p-3 rounded-lg h-full"
                onClick={() => handleSubmit()}
              >
                Submit
              </Button>
            </div>
          </AccordionContent>
        )}
      </AccordionItem>
    </Accordion>
  );
}
