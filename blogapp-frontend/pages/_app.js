import { Providers } from "@/store/providers";
import "@/styles/globals.css";
import { ThemeProvider } from "@/components/theme-provider";
import { useEffect } from "react";

export default function App({ Component, pageProps }) {
  useEffect(() => {
    const checkuser = async () => {
      const res = await fetch("/api/auth/user");
      const data = await res.json();
      return data;
    };
    checkuser().then((data) => {
      const now = new Date();
      if (data.isLoggedIn && now.getTime() >= data.expiry) {
        console.log("Expired")
        fetch("api/auth/refresh", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            id: data.id,
            token: data.refreshToken,
          }),
        });
      }
    });
  }, []);

  return (
    <ThemeProvider attribute="class" defaultTheme="dark" enableSystem>
      <Providers>
        <Component {...pageProps} />
      </Providers>
    </ThemeProvider>
  );
}
