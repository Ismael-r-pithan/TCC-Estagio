import React from "react";
import { useLoading } from "../../hooks/context/useLoading.hook";
import "./loadingAnimation.css";

export function LoadingAnimation() {
  const { loading } = useLoading();

  return loading ? (
    <div className="screenOverlay">
        <div className="loadingAnimation" />
    </div>
  ) : null;
}
