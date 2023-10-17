import { withIronSessionApiRoute } from "iron-session/next";
import { sessionOptions } from "lib/session";

export default withIronSessionApiRoute(async (req, res) => {
    const { method } = req;
    const { postId } = req.query;
    const user = req.session.user

    if (!user.isLoggedIn) {
      res.status(401).json({ statusCode: 401, message: "Unauthorized" });
      return;
    }

    try {
      const response = await fetch("http://localhost:8080/comments?postId="+postId, {
        method,
        headers: {
          "Content-Type": "application/json",
          "Authorization": user.accessToken
        },
      });
      const data = await response.json();
  
      res.status(200).json(data);
    } catch (error) {
      res.status(500).json({ statusCode: 500, message: error.message });
    }
  }, sessionOptions);
  