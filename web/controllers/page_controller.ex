defmodule WebsiteArchiet.PageController do
  use WebsiteArchiet.Web, :controller

  def index(conn, _params) do
    render conn, "index.html"
  end
end
