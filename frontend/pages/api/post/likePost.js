import { withIronSessionApiRoute } from "iron-session/next";
import { sessionOptions } from "lib/session";

export default withIronSessionApiRoute(async (req, res) => {
  const { method } = req;
  const { body } = req;

  const user = req.session.user

  if (!user.isLoggedIn) {
    res.status(401).json({ statusCode: 401, message: "Unauthorized" });
    return;
  }

  if (method === "POST") {
    try {
      const response = await fetch("http://localhost:8080/likes", {
        method,
        headers: {
          "Content-Type": "application/json",
          "Authorization": user.accessToken
        },
        body: JSON.stringify({
          postId: body.postId,
          userId: body.userId,
        }),
      });

      const data = await response.json();
      res.status(200).json(data);
    } catch (error) {
      res.status(500).json({ statusCode: 500, message: error.message });
    }
  } else {
    try {
      const response = await fetch(
        "http://localhost:8080/likes/" + body.likeId,
        {
          method,
          headers: {
            "Content-Type": "application/json",
          },
        }
      );

      const data = await response.json();
      res.status(200).json(data);
    } catch (error) {
      res.status(500).json({ statusCode: 500, message: error.message });
    }
  }
}, sessionOptions);