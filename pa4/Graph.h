/*
 * Name: Mohammad Mirabian
 * CruzID: mmirabia
 * File Name: Graph.h
 * File Purpose: Header file for ADT Graph.c
 *
 */

#ifndef __GRAPH_H__
#define __GRAPH_H__

#include <stdio.h>
#include "List.h"
#define INF -255
#define NIL -254

typedef struct GraphObj* Graph;

/*** Constructors-Destructors ***/
Graph newGraph(int n);
void freeGraph(Graph* pG);
/*** Access functions ***/
int getOrder(Graph G);
int getSize(Graph G);
int getSource(Graph G);
int getParent(Graph G, int u);
int getDist(Graph G, int u);
void getPath(List L, Graph G, int u);
/*** Manipulation procedures ***/
void makeNull(Graph G);
void addEdge(Graph G, int u, int v);
void addArc(Graph G, int u, int v);
void BFS(Graph G, int s);
/*** Other operations ***/
void printGraph(FILE* out, Graph G);


#endif
