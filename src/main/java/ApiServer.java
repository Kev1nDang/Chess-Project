import io.javalin.Javalin;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ApiServer {
    static Map<String, GameSession> games = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        Javalin app = Javalin.create();

        app.before(ctx -> {
            ctx.header("Access-Control-Allow-Origin", "*");
            ctx.header("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
            ctx.header("Access-Control-Allow-Headers", "Content-Type");
        });
        app.options("/*", ctx -> ctx.status(200));

        app.post("/api/games", ctx -> {
            String id = UUID.randomUUID().toString();
            GameSession session = new GameSession();
            games.put(id, session);
            ctx.json(session.toDTO(id));
        });

        app.get("/api/games/{id}", ctx -> {
            GameSession session = games.get(ctx.pathParam("id"));
            if (session == null) {
                ctx.status(404).json(Map.of("message", "Game not found"));
                return;
            }
            ctx.json(session.toDTO(ctx.pathParam("id")));
        });

        app.get("/api/games/{id}/moves", ctx -> {
            GameSession session = games.get(ctx.pathParam("id"));
            if (session == null) {
                ctx.status(404).json(Map.of("message", "Game not found"));
                return;
            }
            int x = Integer.parseInt(ctx.queryParam("x"));
            int y = Integer.parseInt(ctx.queryParam("y"));
            ctx.json(session.legalMovesFrom(x, y));
        });

        app.post("/api/games/{id}/move", ctx -> {
            GameSession session = games.get(ctx.pathParam("id"));
            if (session == null) {
                ctx.status(404).json(Map.of("message", "Game not found"));
                return;
            }
            MoveRequest req = ctx.bodyAsClass(MoveRequest.class);
            session.makeMove(req.fromX, req.fromY, req.toX, req.toY);
            ctx.json(session.toDTO(ctx.pathParam("id")));
        });

        app.exception(IllegalMoveException.class, (e, ctx) -> {
            Map<String, String> body = new HashMap<>();
            body.put("message", e.getMessage());
            ctx.status(400).json(body);
        });

        app.exception(NumberFormatException.class, (e, ctx) -> {
            Map<String, String> body = new HashMap<>();
            body.put("message", "Invalid coordinates");
            ctx.status(400).json(body);
        });

        app.start(7000);
        System.out.println("Chess API listening on http://localhost:7000");
    }
}
