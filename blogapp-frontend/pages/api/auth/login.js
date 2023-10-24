import { withIronSessionApiRoute } from "iron-session/next";
import { sessionOptions } from "lib/session";

export default withIronSessionApiRoute(async (req, res) => {
  const { username, password } = await req.body;

  try {
    const response = await fetch("http://localhost:8080/auth/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        name: username,
        password,
      }),
    });

    const data = await response.json();

    if (!response.ok) {
      res.status(401).json({ message: data.message });
      return;
    }

    const now = new Date();
    const oneWeekLater = new Date(now.getTime() + 7 * 24 * 60 * 60 * 1000);
    console.log(data);
    const user = {
      isLoggedIn: true,
      accessToken: data.accessToken,
      refreshToken: data.refreshToken,
      expiry: oneWeekLater.getTime(),
      username: data.name,
      id: data.id,
      roles: data.roles,
    };

    req.session.user = user;
    await req.session.save();
    res.status(200).json(user);
  } catch (error) {
    console.log(error)
    res.status(500).json({ message: error.message });
  }
}, sessionOptions);
