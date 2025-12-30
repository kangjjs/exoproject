import { useEffect, useState } from "react";

export default function App() {
  const [choice, setChoice] = useState("");
  const [result, setResult] = useState(null);
  const [message, setMessage] = useState("");

  // 결과 조회
  async function loadResult() {
    const res = await fetch("/api/votes/result");
    const data = await res.json();
    setResult(data);
  }

  // 투표
  async function submitVote() {
    if (!choice) {
      alert("하나를 선택해주세요");
      return;
    }

    await fetch("/api/votes", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ choice }),
    });

    setMessage("투표 완료!");
    setChoice("");
    loadResult();
  }

  useEffect(() => {
    loadResult();
  }, []);

  return (
    <div className="container py-5" style={{ maxWidth: 600 }}>
      <h2 className="mb-4 text-center">🍜 라면 투표</h2>

      {/* 투표 영역 */}
      <div className="card mb-4">
        <div className="card-body text-center">
          <div className="form-check mb-2">
            <input
              className="form-check-input"
              type="radio"
              name="vote"
              value="JJAJANG"
              checked={choice === "JJAJANG"}
              onChange={() => setChoice("JJAJANG")}
            />
            <label className="form-check-label">짜장면</label>
          </div>

          <div className="form-check mb-3">
            <input
              className="form-check-input"
              type="radio"
              name="vote"
              value="JJAMBONG"
              checked={choice === "JJAMBONG"}
              onChange={() => setChoice("JJAMBONG")}
            />
            <label className="form-check-label">짬뽕</label>
          </div>

          <button className="btn btn-primary" onClick={submitVote}>
            투표하기
          </button>

          {message && <div className="alert alert-success mt-3">{message}</div>}
        </div>
      </div>

      {/* 결과 영역 */}
      <div className="card">
        <div className="card-header">📊 현재 결과</div>
        <div className="card-body">
          {result ? (
            <>
              <p>짜장면 : <strong>{result.jjajang}</strong> 표</p>
              <p>짬뽕 : <strong>{result.jjambong}</strong> 표</p>
            </>
          ) : (
            <p>로딩 중...</p>
          )}
        </div>
      </div>
    </div>
  );
}
