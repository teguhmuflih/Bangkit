import os
import sqlalchemy
from flask import abort


def query(request):

    # Set CORS headers for the main request
    headers = {
        'Access-Control-Allow-Origin': '*'
    }

    if request.method == 'OPTIONS':
        # Allows GET requests from any origin with the Content-Type
        # header and caches preflight response for an 3600s
        headers = {
            'Access-Control-Allow-Origin': '*',
            'Access-Control-Allow-Methods': 'GET',
            'Access-Control-Allow-Headers': 'Content-Type',
            'Access-Control-Max-Age': '3600'
        }

        return ('', 204, headers)
        
    elif request.method == "POST":
        content_type = request.headers["content-type"]
        if content_type == "application/json":
            request_json = request.get_json(silent=True)

            if request_json:
                if "query" not in request_json:
                    return "JSON is invalid, or missing a 'query' property"

                driver_name = "mysql+pymysql"
                query_string = dict(
                    {
                        "unix_socket": f"/cloudsql/{os.environ.get('CONNECTION_NAME', '-')}"
                    }
                )

                db_user = os.environ.get("USER", "-")
                db_pass = os.environ.get("PASSWORD", "-")
                db_name = os.environ.get("DATABASE", "-")

                db = sqlalchemy.create_engine(
                    sqlalchemy.engine.url.URL(
                        drivername=driver_name,
                        username=db_user,  # e.g. "my-database-user"
                        password=db_pass,  # e.g. "my-database-password"
                        database=db_name,  # e.g. "my-database-name"
                        query=query_string,
                    ),
                    pool_size=5,
                    max_overflow=2,
                    pool_timeout=30,
                    pool_recycle=1800,
                )

                try:
                    with db.connect() as conn:
                        result = conn.execute(sqlalchemy.text(request_json["query"]))
                        return str('{"result":['+",\n".join([str(dict(row)).replace("'", "\"") for row in result])+']}')
                except Exception as e:
                    return "Error: {}".format(str(e))

            else:
                return abort(405)
        else:
            return abort(405)
    else:
        return abort(405)