import Link from "next/link";

import { useEffect, useState } from "react";
import { useRouter } from "next/router";

const Navbar = ({ children }) => {
  const router = useRouter();
  const [user, setUser] = useState(null);

  const checkuser = async () => {
    const res = await fetch("/api/auth/user");
    const data = await res.json();
    setUser(data);
  };

  useEffect(() => {
    checkuser();
  }, [router]);

  const signOut = async () => {
    const res = await fetch("/api/auth/logout");
    router.reload();
    if (!res.ok) {
      alert("Something went wrong");
    }
    checkuser();
  };

  return (
    <div className="flex flex-col gap-4">
      <div className="flex justify-between items-center">
        <div className="flex gap-4">
          <Link href="/profile" className="text-xl font-bold cursor-pointer">
            Profile
          </Link>
          <Link href="/posts" className="text-xl font-semibold cursor-pointer">
            Posts
          </Link>
        </div>
        <div className="flex gap-4">
          {user?.isLoggedIn ? (
            <div className="text-xl font-semibold">
              {user.username}{" "}
              <button onClick={() => signOut()}>Sign Out</button>
            </div>
          ) : (
            <Link href="/login" className="text-xl font-semibold">
              Login
            </Link>
          )}
        </div>
      </div>
      <main>{children}</main>
      <footer>Footer</footer>
    </div>
  );
};

export default Navbar;
