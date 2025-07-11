import type { PageLoad } from "./$types";

export const load: PageLoad = async ({ params, fetch }) => {
  const res = await fetch(`/api/posts/${params.post_id}`)
  const post = await res.json

  return {
    post
  }
}