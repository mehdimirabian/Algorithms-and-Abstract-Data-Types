/*
 * Name: Mohammad Mirabian
 * CruzID: mmirabia
 * File Name: Graph.c
 * File Purpose: ADT for graph operations
 *
 */

#include <stdlib.h>

#include "Graph.h"

static void insertsort(List L, int u);

#define WHITE 10000001
#define BLACK 19999991
#define GRAY  -6666669
struct GraphObj {
	List *array;
	int *colors;
	int *parents;
	int *distances;
	int order; // number of vertices
	int size; // number of edges
	int source;
};

//returns a Graph pointing to a newly created GraphObj
//representing a graph having n vertices and no edges.
Graph newGraph(int n) {
	Graph new = malloc(sizeof(struct GraphObj));
	new->array = calloc(n + 1, sizeof(List));
	new->colors = calloc(n + 1, sizeof(int));
	new->parents = calloc(n + 1, sizeof(int));
	new->distances = calloc(n + 1, sizeof(int));

	new->source = NIL;
	new->order = n;
	new->size = 0;
	int i;
	for (i = 0; i < n + 1; i++) {
		new->array[i] = newList();
		new->colors[i] = WHITE;
		new->parents[i] = NIL; // no parents as of yet
		new->distances[i] = INF; // no edges so no distances
	}
	return new;
}
// frees all dynamic memory associated with the Graph *pG,
//then sets the handle *pG to NULL.
void freeGraph(Graph* pG) {
	Graph freed = *pG;
	int i;
	for (i = 0; i < (freed->order) + 1; i++) {
		freeList(&(freed->array[i]));
	}
	free(freed->array);
	free(freed->colors);
	free(freed->parents);
	free(freed->distances);
	free(*pG);
	*pG = NULL;
}

/*** Access functions ***/
// returns the number of vertices
int getOrder(Graph G) {
	return G->order;
}
// returns the number of edges
int getSize(Graph G) {
	return G->size;
}
// returns the source vertex
int getSource(Graph G) {
	return G->source;
}
// returns the parent vertex of the chosen vertex
int getParent(Graph G, int u) {
	if (u < 1 || u > getOrder(G)) {
		printf("invalid parameter");
		return 0;
	}
	return G->parents[u];
}

// returns the distance of the vertex from source
int getDist(Graph G, int u) {
	if (getSource(G) == NIL) {
		return INF;
	}
	if (u < 1 || u > getOrder(G)) {
		printf("invalid parameter");
		return 0;
	}
	return G->distances[u];
}

// computes the shortest path from vertex to source
void getPath(List L, Graph G, int u) {
	if (getSource(G) == NIL) {
		printf("Source is Empty");
	}
	if (u < 1 || u > getOrder(G)) {
		printf("invalid parameter");
		return;
	}
	int src = G->source;
	if (u == src) {
		prepend(L, src);
		return;
	} else if (G->parents[u] == NIL) {
		append(L, NIL);
	} else {
		prepend(L, u);
		getPath(L, G, G->parents[u]);
	}
}

/*** Manipulation procedures ***/
// clear's all the edges
void makeNull(Graph G) {
	int i;
	for (i = 0; i < G->order + 1; i++) {
		clear(G->array[i]);
	}
}

// creates an edge between vertices
// precondition, the vertex numbers must be > 1 and < getOrder(G)
void addEdge(Graph G, int u, int v) {
	if (u < 1 || u > getOrder(G) || v < 1 || v > getOrder(G)) {
		printf("preconditions not met\n");
		printf("%d %d\n", u, v);
		return;
	}
	List U = G->array[u];
	List V = G->array[v];
	insertsort(U, v);
	insertsort(V, u);
}

// private function to append vertices in adjacency list in order
static void insertsort(List L, int u) {
	if (length(L) == 0) {
		append(L, u);
		return;
	}
	for (moveFront(L); index(L) >= 0; moveNext(L)) {
		int big = get(L);
		if (u < big) {
			insertBefore(L, u);
			break;
		}
	}
	if (index(L) == -1) {
		append(L, u);
	}
}

// adds a directed edge
void addArc(Graph G, int u, int v) {
	if (u < 1 || u > getOrder(G) || v < 1 || v > getOrder(G)) {
		printf("preconditions not met");
	}
	List U = G->array[u];
	insertsort(U, v);
}

// BFS algorithm
void BFS(Graph G, int s) {
	int u;
	for (u = 0; u < G->order + 1; u++) {
		if (u != s) {
			G->colors[u] = WHITE;
			G->distances[u] = INF;
			G->parents[u] = NIL;
		}
	}
	// sets the source vetex
	G->source = s;
	G->colors[s] = GRAY;
	G->distances[s] = 0;
	G->parents[s] = NIL;
	// creates List object as Q
	List Q = newList();
	prepend(Q, s);
	while (length(Q) > 0) {
		int u = back(Q);
		deleteBack(Q);
		List adj = G->array[u];
		for (moveFront(adj); index(adj) >= 0; moveNext(adj)) {
			int vertex = get(adj);
			if (G->colors[vertex] == WHITE) {
				G->colors[vertex] = GRAY;
				G->distances[vertex] = G->distances[u] + 1;
				G->parents[vertex] = u;
				prepend(Q, vertex);
			}
		}

		G->colors[u] = BLACK;
	}
	freeList(&Q);
}

/*** Other operations ***/
// print function to print the graph in pretty format
void printGraph(FILE* out, Graph G) {
	if (out == NULL || G == NULL) {
		printf("Passed in NUll parameters");
		return;
	}

	int i;
	for (i = 1; i < getOrder(G) + 1; i++) {
		fprintf(out, "%d: ", i);
		printList(out, G->array[i]);
		fprintf(out, "\n");
	}
}
