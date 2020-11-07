export async function Get(url, access_token = "") {
  if (access_token.length === 0) {
    const response = await fetch(url, {
      method: "GET",

      headers: {
        Accept: "application/json",
      },
    });
    return response;
  } else {
    const response = await fetch(url, {
      method: "GET",

      headers: {
        Accept: "application/json",
        Authorization: "Bearer " + access_token,
      },
    });
    return response;
  }
}

export async function Post(url, request, access_token = "") {
  if (access_token.length === 0) {
    const response = await fetch(url, {
      method: "POST",
      mode: "cors",
      body: JSON.stringify(request),
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
        "Cache-Control": "no-cache",
      },
    });

    return response;
  } else {
    const response = await fetch(url, {
      method: "POST",
      mode: "cors",
      body: JSON.stringify(request),
      headers: {
        "Content-Type": "application/json",
        "Cache-Control": "no-cache",
        Accept: "application/json",
        Authorization: "Bearer " + access_token,
      },
    });

    return response;
  }
}

export async function Put(url, request, access_token = "") {
  const response = await fetch(url, {
    method: "Put",
    mode: "cors",
    body: JSON.stringify(request),
    headers: {
      "Content-Type": "application/json",
      "Cache-Control": "no-cache",
      Accept: "application/json",
      Authorization: "Bearer " + access_token,
    },
  });

  return response;
}

export async function Delete(url, access_token = "") {
  if (access_token.length === 0) {
    const response = await fetch(url, {
      method: "DELETE",

      headers: {
        Accept: "application/json",
      },
    });
    return response;
  } else {
    const response = await fetch(url, {
      method: "DELETE",

      headers: {
        Accept: "application/json",
        Authorization: "Bearer " + access_token,
      },
    });
    return response;
  }
}