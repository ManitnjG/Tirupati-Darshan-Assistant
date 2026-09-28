# Reliability rules

1. Predictions, derived data and official data are separate types.
2. No prediction becomes official without independently verified provenance.
3. The app never marks payment as booking confirmation.
4. A confirmed booking requires the confirmation state plus an official reference.
5. Background Android work uses constrained, rate-limited periodic scheduling and exponential backoff.
6. Official booking opens in Custom Tabs with external-browser fallback.
7. CAPTCHA, OTP, waiting rooms, queues and payment authorization remain user/TTD controlled.
8. Sensitive identity data must be masked in UI/logs and stored locally using platform-backed encryption before production.
9. If a live official collector is unavailable or changes format, the app reports unknown/stale status rather than inventing availability.
10. Historical observations must carry source provenance before being accepted by the predictor.
