//goog.global['describe']("lib", () => {
//    goog.global['it']("should say hi", () => {
//        console.log("111");
//    })
//})
describe("simple test suite", () => {
    it("simple test", () => {
        expect(1+1).toBe(2);
    })
})