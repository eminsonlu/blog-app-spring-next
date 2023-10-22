export default async function handler(req, res) {
  const method = "POST";
  const body = req.body;
  try {
    const response = await fetch("http://localhost:8080/auth/register", {
      method,
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        name: body.username,
        email: body.email,
        password: body.password,
      }),
    });
    const data = await response.json();

    res.status(200).json(data);
  } catch (error) {
    res.status(500).json({ statusCode: 500, message: error.message });
  }
}
