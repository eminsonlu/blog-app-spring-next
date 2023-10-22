import { withIronSessionApiRoute } from "iron-session/next";
import { sessionOptions } from "lib/session";

export default withIronSessionApiRoute(async (req, res) => {
  const { id, token } = await req.body;

  try {
    const response = await fetch("http://localhost:8080/auth/refresh", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        userId: id,
        refreshToken: token,
      }),
    });

    const data = await response.json();

    const now = new Date();
    const oneWeekLater = new Date(now.getTime() + 7 * 24 * 60 * 60 * 1000);

    const user = {
      isLoggedIn: true,
      accessToken: data.accessToken,
      refreshToken: refreshToken,
      expiry: oneWeekLater.getTime(),
      username: data.name,
      id: data.id,
      roles: data.roles,
    };
    req.session.user = user;
    await req.session.save();
    res.status(200).json(user);
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
}, sessionOptions);
