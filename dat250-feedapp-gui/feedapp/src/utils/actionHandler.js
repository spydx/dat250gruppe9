export async function login(loginRequest) {
    const url = "http://localhost:8080/api/auth/login";
    const response = await fetch(url, {
        method: 'POST',
        body: JSON.stringify(loginRequest),
        headers: {
            'Content-Type': 'application/json'
        },

    });

    return response;
}

export function signup(signupRequest) {
    return new Request({
        url: "http://localhost:8080/api/auth/register",
        method: 'POST',
        body: JSON.stringify(signupRequest)
    });
}